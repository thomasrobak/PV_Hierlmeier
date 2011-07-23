package hierlmeier



import org.junit.*
import grails.test.mixin.*


@TestFor(PositionController)
@Mock(Position)
class PositionControllerTests {


    @Test
    void testIndex() {
        controller.index()
        assert "/position/list" == response.redirectedUrl
    }

    @Test
    void testList() {

        def model = controller.list()

        assert model.positionInstanceList.size() == 0
        assert model.positionInstanceTotal == 0

    }

    @Test
    void testCreate() {
       def model = controller.create()

       assert model.positionInstance != null


    }

    @Test
    void testSave() {
        controller.save()

        assert model.positionInstance != null
        assert view == '/position/create'

        // TODO: Populate valid properties

        controller.save()

        assert response.redirectedUrl == '/position/show/1'
        assert controller.flash.message != null
        assert Position.count() == 1
    }


    @Test
    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/position/list'


        def position = new Position()

        // TODO: populate domain properties

        assert position.save() != null

        params.id = position.id

        def model = controller.show()

        assert model.positionInstance == position
    }

    @Test
    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/position/list'


        def position = new Position()

        // TODO: populate valid domain properties

        assert position.save() != null

        params.id = position.id

        def model = controller.edit()

        assert model.positionInstance == position
    }

    @Test
    void testUpdate() {

        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/position/list'

        response.reset()


        def position = new Position()

        // TODO: populate valid domain properties

        assert position.save() != null

        // test invalid parameters in update
        params.id = position.id

        controller.update()

        assert view == "/position/edit"
        assert model.positionInstance != null

        position.clearErrors()

        // TODO: populate valid domain form parameter
        controller.update()

        assert response.redirectedUrl == "/position/show/$position.id"
        assert flash.message != null
    }

    @Test
    void testDelete() {
        controller.delete()

        assert flash.message != null
        assert response.redirectedUrl == '/position/list'

        response.reset()

        def position = new Position()

        // TODO: populate valid domain properties
        assert position.save() != null
        assert Position.count() == 1

        params.id = position.id

        controller.delete()

        assert Position.count() == 0
        assert Position.get(position.id) == null
        assert response.redirectedUrl == '/position/list'


    }


}